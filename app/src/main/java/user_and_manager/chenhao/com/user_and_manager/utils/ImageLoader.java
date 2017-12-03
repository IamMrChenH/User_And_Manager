package user_and_manager.chenhao.com.user_and_manager.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.util.DisplayMetrics;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class ImageLoader
{

    public interface ImageLoaderRun
    {
//        public String getBitmapPath();

        public String DownBitmapPath();

    }

    ImageLoaderRun mImageLoaderRun;


    /**
     *
     */
    private static ImageLoader mInstance;
    /**
     * 图片缓存的核心对象
     */
    private LruCache<String, Bitmap> mLruCache;
    /**
     * 线程池
     */
    private ExecutorService mThreadPool;
    /**
     * 线程个数上限
     */
    private static final int DEFULT_THREAD_COUNT = 3;
    /**
     * 队列的调度方式
     */
    private static Type mType = Type.LIFO;
    /**
     * 任务队列哦
     */
    private LinkedList<Runnable> mTaskQueue;
    /**
     * 轮询线程
     */
    private Thread mPoolThread;
    /**
     * 发消息？
     */
    private Handler mPoolThreadHandler;
    /**
     * UI线程中的Handle
     */
    private Handler mUIHandler;

    private Semaphore mSemaphorePoolThreadHandle = new Semaphore(0);

    private Semaphore mSemaphoreThreadPool;

    public enum Type
    {
        FIFO, LIFO
    }

    private ImageLoader(int threadCount, Type type)
    {
        init(threadCount, type);
    }

    /**
     * 初始化
     *
     * @param threadCount
     * @param type
     */
    private void init(int threadCount, Type type)
    {
        // TODO Auto-generated method stub
        mPoolThread = new Thread()
        {
            /**
             * 后台轮询线程
             */
            @Override
            public void run()
            {
                Looper.prepare();
                mPoolThreadHandler = new Handler()
                {
                    @Override
                    public void handleMessage(Message msg)
                    {
                        // 线程池去取出一个任务执行
                        mThreadPool.execute(getTask());
                        try
                        {
                            mSemaphoreThreadPool.acquire();
                        } catch (InterruptedException e)
                        {

                        }
                    }

                };
                // 释放一个信号量 完全不懂，我已经要哭了。
                mSemaphorePoolThreadHandle.release();
                Looper.loop();
            }
        };

        mPoolThread.start();
        // 获取应用最大可用内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheMemory = maxMemory / 8;
        mLruCache = new LruCache<String, Bitmap>(maxMemory)
        {
            /**
             * 这个方法用于测量每个Bitmap图片的大小
             */
            @Override
            protected int sizeOf(String key, Bitmap value)
            {
                return value.getRowBytes() * value.getHeight();
            }
        };
        // 创建线程池
        mThreadPool = Executors.newFixedThreadPool(threadCount);
        mTaskQueue = new LinkedList<Runnable>();
        mType = type;
        mSemaphoreThreadPool = new Semaphore(threadCount);
    }

    private Runnable getTask()
    {
        if (mType == Type.FIFO)
        {
            return mTaskQueue.removeFirst();
        } else if (mType == Type.LIFO)
        {
            return mTaskQueue.removeLast();
        }

        return null;
    }

    public static ImageLoader getInstance(int threadCount, Type type)
    {
        if (mInstance == null)
        {
            synchronized (ImageLoader.class)
            {
                if (mInstance == null)
                    mInstance = new ImageLoader(threadCount, type);
            }
        }

        return mInstance;

    }

    public static ImageLoader getInstance()
    {
        if (mInstance == null)
        {
            synchronized (ImageLoader.class)
            {
                if (mInstance == null)
                    mInstance = new ImageLoader(DEFULT_THREAD_COUNT, Type.FIFO);
            }
        }

        return mInstance;

    }

    /**
     * @param path
     * @param imageView
     */
    public void loadImage(final String path, final ImageView imageView)
    {
        if (imageView != null)
            imageView.setTag(path);
        if (mUIHandler == null)
        {
            mUIHandler = new Handler()
            {
                public void handleMessage(Message msg)
                {
                    // 获取图片 为imageView 设置回调图片
                    ImageBeanHolder holder = (ImageBeanHolder) msg.obj;
                    Bitmap bm = holder.bitmap;
                    ImageView imageView = holder.imageView;
                    String path = holder.path;
                    if (imageView.getTag().toString().equals(path))
                    {
                        imageView.setImageBitmap(bm);
                    }
                }

                ;
            };
        }
        Bitmap bm = getBitmapFromLruCache(path);
        if (bm != null)
        {
            refreashBitmap(path, imageView, bm);
        } else
        {
            addTasks(new Runnable()
            {
                @Override
                public void run()
                {
                    // 加载图片
                    // 图片的压缩
                    // 1、获得图片需要显示的大小
                    ImageSize imageSize = getImageViewSize(imageView);
                    // 2压缩图片
                    Bitmap bm = decodeSampledBitmapFromPath(path,
                            imageSize.width, imageSize.height);
                    // 3把图片加入到缓存中
                    addBitmapToLruCache(path, bm);
                    refreashBitmap(path, imageView, bm);
                    mSemaphoreThreadPool.release();

                }

            });
        }
    }

    /******************************************************************************/

//    public void loadImage(String path, final ImageView imageView, final ImageLoaderRun
//            mImageLoaderRun)
//    {
////        String path = mImageLoaderRun.getBitmapPath();
//        if (getBitmapFromLruCache(path) != null)
//        {
//            loadImage(path, imageView);
//        } else
//        {
//            path = mImageLoaderRun.DownBitmapPath();
//            loadImage(path, imageView);
//        }
//
//
//    }


    /**
     * @param path
     * @param imageView
     * @param bm
     */
    private void refreashBitmap(final String path, final ImageView imageView,
                                Bitmap bm)
    {
        Message message = Message.obtain();
        ImageBeanHolder holder = new ImageBeanHolder();
        holder.bitmap = bm;
        holder.imageView = imageView;
        holder.path = path;
        message.obj = holder;
        mUIHandler.sendMessage(message);
    }

    /**
     * 将图片加入 （LruCache）缓存
     *
     * @param path
     * @param bm
     */
    protected void addBitmapToLruCache(String path, Bitmap bm)
    {
        // TODO Auto-generated method stub
        if (getBitmapFromLruCache(path) == null)
        {
            if (bm != null)
                mLruCache.put(path, bm);

        }
    }

    /**
     * 根据图片需要显示的宽和高 对图片进行压缩
     *
     * @param path
     * @param width
     * @param height
     * @return
     */
    private Bitmap decodeSampledBitmapFromPath(String path, int width,
                                               int height)
    {
        // 获得图片 的宽和高 并不把图片加载到内存中
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        options.inSampleSize = caculateInSampleSize(options, width, height);
        // 使用获得到的inSampleSize 再次解析图片
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        return bitmap;
    }


    /**
     * 根据需要的宽和高 以及实际的宽和高 计算 SamoleSize
     *
     * @param options
     * @return
     */
    private int caculateInSampleSize(Options options, int reqWidth, int reqHeight)
    {

        int width = options.outWidth;
        int height = options.outHeight;

        int inSampleSize = 1;
        if (width > reqWidth || height > reqHeight)
        {
            int widthRadio = Math.round(width * 1.0f / reqWidth);
            int heightRadio = Math.round(height * 1.0f / reqHeight);
            inSampleSize = Math.max(widthRadio, heightRadio);
        }
        return inSampleSize;
    }

    /**
     * 根据ImageView 获得适当的宽和高
     *
     * @param imageView
     * @return
     */
    private ImageSize getImageViewSize(ImageView imageView)
    {
        // TODO Auto-generated method stub
        ImageSize imageSize = new ImageSize();
        DisplayMetrics displayMetrics = imageView.getContext().getResources()
                .getDisplayMetrics();

        LayoutParams lp = imageView.getLayoutParams();
        // int width = (lp.width == LayoutParams.WRAP_CONTENT ? 0 : imageView
        // .getWidth());
        int width = imageView.getWidth();// 获取ImageView的实际宽度
        if (width <= 0)
        {
            width = lp.width;// 获得imageView在layout中声明的宽度
        }
        if (width <= 0)
        {
            width = imageView.getMaxWidth();// 最大宽度
        }
        if (width <= 0)
        {
            width = displayMetrics.widthPixels;// 屏幕宽度
        }

        int height = imageView.getHeight();// 获取ImageView的实际宽度
        if (height <= 0)
        {
            height = lp.height;// 获得imageView在layout中声明的宽度
        }
        if (height <= 0)
        {
            height = imageView.getMaxHeight();// 最大宽度
        }
        if (height <= 0)
        {
            height = displayMetrics.heightPixels;// 屏幕宽度
        }
        imageSize.width = width;
        imageSize.height = height;
        return imageSize;
    }

    protected synchronized void addTasks(Runnable runnable)
    {
        // TODO Auto-generated method stub
        mTaskQueue.add(runnable);
        try
        {
            if (mPoolThreadHandler == null)
                mSemaphorePoolThreadHandle.acquire();
        } catch (InterruptedException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        mPoolThreadHandler.sendEmptyMessage(0x110);
    }

    /**
     * 根据Path 在缓存中获取bitmap
     *
     * @param key
     * @return
     */
    private Bitmap getBitmapFromLruCache(String key)
    {
        // TODO Auto-generated method stub
        return mLruCache.get(key);
    }

    private class ImageSize
    {

        int width;
        int height;
    }

    public class ImageBeanHolder
    {
        Bitmap bitmap;
        ImageView imageView;
        String path;
    }
}