/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cricteam.imagepicker.utils;

import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Iterator;
import java.util.WeakHashMap;

/**
 * This class provides several utilities to cancel bitmap decoding.
 * <p/>
 * The function decodeFileDescriptor() is used to decode a bitmap. During
 * decoding if another thread wants to cancel it, it calls the function
 * cancelThreadDecoding() specifying the Thread which is in decoding.
 * <p/>
 * cancelThreadDecoding() is sticky until allowThreadDecoding() is called.
 * <p/>
 * You can also cancel decoding for a set of threads using ThreadSet as
 * the parameter for cancelThreadDecoding. To put a thread into a ThreadSet,
 * use the add() method. A ThreadSet holds (weak) references to the threads,
 * so you don't need to remove Thread from it if some thread dies.
 */
@SuppressWarnings("ALL")
public class BitmapManager {

    private static final String TAG = "BitmapManager";
    @Nullable
    private static BitmapManager sManager = null;
    private final WeakHashMap<Thread, ThreadStatus> mThreadStatus =
            new WeakHashMap<>();

    private BitmapManager() {

    }

    @Nullable
    public static synchronized BitmapManager instance() {

        if (sManager == null) {
            sManager = new BitmapManager();
        }
        return sManager;
    }

    /**
     * Get thread status and create one if specified.
     */
    private synchronized ThreadStatus getOrCreateThreadStatus(Thread t) {

        ThreadStatus status = mThreadStatus.get(t);
        if (status == null) {
            status = new ThreadStatus();
            mThreadStatus.put(t, status);
        }
        return status;
    }

    public synchronized void cancelThreadDecoding(@NonNull ThreadSet threads) {

        for (Thread t : threads) {
            cancelThreadDecoding(t);
        }
    }

    public synchronized void cancelThreadDecoding(Thread t) {

        ThreadStatus status = getOrCreateThreadStatus(t);
        status.mState = State.CANCEL;
        if (status.mOptions != null) {
            status.mOptions.requestCancelDecode(); // TODO : Depreciated in 24, look later for alternate.
        }

        // Wake up threads in waiting list
        notifyAll();
    }


    private enum State {CANCEL, ALLOW}

    private static class ThreadStatus {

        @NonNull
        public State mState = State.ALLOW;
        @Nullable
        public BitmapFactory.Options mOptions;

        @Nullable
        @Override
        public String toString() {

            String s;
            if (mState == State.CANCEL) {
                s = "Cancel";
            } else if (mState == State.ALLOW) {
                s = "Allow";
            } else {
                s = "?";
            }
            s = "thread state = " + s + ", options = " + mOptions;
            return s;
        }
    }

    public static class ThreadSet implements Iterable<Thread> {

        private final WeakHashMap<Thread, Object> mWeakCollection =
                new WeakHashMap<>();

        public void add(Thread t) {

            mWeakCollection.put(t, null);
        }

        public void remove(Thread t) {

            mWeakCollection.remove(t);
        }

        @Override
        @NonNull
        public Iterator<Thread> iterator() {

            return mWeakCollection.keySet().iterator();
        }
    }
}
