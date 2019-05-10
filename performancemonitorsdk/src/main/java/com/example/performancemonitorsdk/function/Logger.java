package com.example.performancemonitorsdk.function;

import android.content.Context;
import android.os.Process;
import android.util.Log;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.util.Calendar;

public class Logger {

    private static boolean isWriter;
    private static Level currentLevel;
    private static String pkgName;
    private static FileOutputStream fos;
    private static OutputStreamWriter osWriter;
    private static BufferedWriter writer;

    public enum Level {

        VERBOSE(Log.VERBOSE),

        DEBUG(Log.DEBUG),

        INFO(Log.INFO),

        WARN(Log.WARN),

        ERROR(Log.ERROR),

        ASSERT(Log.ASSERT),

        CLOSE(Log.ASSERT + 1);

        int value;

        Level(int value) {
            this.value = value;
        }
    }

    public static final void initialize(Context appCtx, boolean isWriter, Level level) {
        currentLevel = level;
        if (level == Level.CLOSE) {
            Logger.isWriter = false;
            return;
        }
        Logger.isWriter = isWriter;
        if (!Logger.isWriter) {//不保存日志到文件
            return;
        }
        String logFoldPath = appCtx.getExternalCacheDir().getAbsolutePath() + "/../log/";
        pkgName = appCtx.getPackageName();
        File logFold = new File(logFoldPath);
        boolean flag = false;
        if (!logFold.exists())
            flag = logFold.mkdirs();
        else
            flag=true;
        if (!flag) {
            Logger.isWriter = false;
            return;
        }
        String logFilePath = logFoldPath + getCurrentDate() + ".log";
        try {
            File logFile = new File(logFilePath);
            flag=false;
            if (!logFile.exists())
                flag = logFile.createNewFile();
            else {
                if (logFile.delete())
                    flag = logFile.createNewFile();
                else
                    flag=false;
            }
            Logger.isWriter = flag;
            if (Logger.isWriter) {
                fos = new FileOutputStream(logFile);
                osWriter = new OutputStreamWriter(fos);
                writer = new BufferedWriter(osWriter);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Logger.isWriter = false;
        }
    }

    public static final void i(String tag, String msg) {
        if (currentLevel.value > Level.INFO.value)
            return;
        if (isWriter) {
            write(tag, msg, "I",null);
        }
        Log.i(tag, msg);
    }

    public static final void i(String tag, String msg, Throwable throwable) {
        if (currentLevel.value > Level.INFO.value)
            return;
        if (isWriter) {
            write(tag, msg, "I", throwable);
        }
        Log.i(tag, msg, throwable);
    }

    public static final void v(String tag, String msg) {
        if (currentLevel.value > Level.VERBOSE.value)
            return;
        if (isWriter) {
            write(tag, msg, "V",null);
        }
        Log.v(tag, msg);
    }

    public static final void v(String tag, String msg, Throwable throwable) {
        if (currentLevel.value > Level.VERBOSE.value)
            return;
        if (isWriter) {
            write(tag, msg, "V", throwable);
        }
        Log.v(tag, msg, throwable);
    }

    public static final void d(String tag, String msg) {
        if (currentLevel.value > Level.DEBUG.value)
            return;
        if (isWriter) {
            write(tag, msg, "D",null);
        }
        Log.d(tag, msg);
    }

    public static final void d(String tag, String msg, Throwable throwable) {
        if (currentLevel.value > Level.DEBUG.value)
            return;
        if (isWriter) {
            write(tag, msg, "D", throwable);
        }
        Log.d(tag, msg, throwable);
    }

    public static final void e(String tag, String msg) {
        if (currentLevel.value > Level.ERROR.value)
            return;
        if (isWriter) {
            write(tag, msg, "E",null);
        }
        Log.e(tag, msg);
    }

    public static final void e(String tag, String msg, Throwable throwable) {
        if (currentLevel.value > Level.ERROR.value)
            return;
        if (isWriter) {
            write(tag, msg, "E", throwable);
        }
        Log.e(tag, msg, throwable);
    }

    public static final void w(String tag, String msg) {
        if (currentLevel.value > Level.WARN.value)
            return;
        if (isWriter) {
            write(tag, msg, "W",null);
        }
        Log.w(tag, msg);
    }

    public static final void w(String tag, String msg, Throwable throwable) {
        if (currentLevel.value > Level.WARN.value)
            return;
        if (isWriter) {
            write(tag, msg, "W", throwable);
        }
        Log.w(tag, msg, throwable);
    }

    public static final void i(Object target, String msg) {
        i(target.getClass().getSimpleName(), msg);
    }

    public static final void i(Object target, String msg, Throwable throwable) {
        i(target.getClass().getSimpleName(), msg, throwable);
    }

    public static final void v(Object target, String msg) {
        v(target.getClass().getSimpleName(), msg);
    }

    public static final void v(Object target, String msg, Throwable throwable) {
        v(target.getClass().getSimpleName(), msg, throwable);
    }

    public static final void d(Object target, String msg) {
        d(target.getClass().getSimpleName(), msg);
    }

    public static final void d(Object target, String msg, Throwable throwable) {
        d(target.getClass().getSimpleName(), msg, throwable);
    }

    public static final void e(Object target, String msg) {
        e(target.getClass().getSimpleName(), msg);
    }

    public static final void e(Object target, String msg, Throwable throwable) {
        e(target.getClass().getSimpleName(), msg, throwable);
    }

    public static final void w(Object target, String msg) {
        w(target.getClass().getSimpleName(), msg);
    }

    public static final void w(Object target, String msg, Throwable throwable) {
        w(target.getClass().getSimpleName(), msg, throwable);
    }

    private static final void write(String tag, String msg, String level, Throwable throwable) {
        String timeStamp = DateFormat.getInstance().format(Calendar.getInstance().getTime());
        try {
            writer.write(String.format("%s", timeStamp+" "+ "ThreadID:" + Process.myTid() +"/"+pkgName+"/"+ level+"/"+tag+": "));
            writer.write(msg);
            writer.newLine();
            writer.flush();
            osWriter.flush();
            fos.flush();
            if (throwable != null)
                saveCrash(throwable);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveCrash(Throwable throwable) throws IOException {
        StringWriter sWriter = new StringWriter();
        PrintWriter pWriter = new PrintWriter(sWriter);
        throwable.printStackTrace(pWriter);
        Throwable cause = throwable.getCause();
        while (cause != null) {
            cause.printStackTrace(pWriter);
            cause = cause.getCause();
        }
        pWriter.flush();
        pWriter.close();
        sWriter.flush();
        String crashInfo = writer.toString();
        sWriter.close();
        writer.write(crashInfo);
        writer.newLine();
        writer.flush();
        osWriter.flush();
        fos.flush();
    }

    private static String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        return year+"_"+month+"_"+day+"_"+hour+"_"+minute+"_"+second;
    }
}
