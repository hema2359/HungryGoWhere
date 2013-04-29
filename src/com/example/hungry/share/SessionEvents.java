/*
 * Copyright 2010 Facebook, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.hungry.share;

import java.util.LinkedList;

import android.os.Bundle;

public class SessionEvents {

    private static AuthListener mAuthListener;
    private static PostListener mPostListener;
   // private static GetListener mGetListener;
    private static LogoutListener mLogoutListener;

    /**
     * Associate the given listener with this Facebook object. The listener's
     * callback interface will be invoked when authentication events occur.
     * 
     * @param listener
     *            The callback object for notifying the application when auth
     *            events happen.
     */
    public static void addAuthListener(AuthListener listener) {
        mAuthListener = listener;
    }

    /**
     * Remove the given listener from the list of those that will be notified
     * when authentication events occur.
     * 
     * @param listener
     *            The callback object for notifying the application when auth
     *            events happen.
     */
    public static void removeAuthListener() {
        mAuthListener = null;
    }

    /**
     * Associate the given listener with this Facebook object. The listener's
     * callback interface will be invoked when logout occurs.
     * 
     * @param listener
     *            The callback object for notifying the application when log out
     *            starts and finishes.
     */
    public static void addPostListener(PostListener listener) {
        mPostListener = listener;
    }
    
   /* public static void addGetListener(GetListener listener) {
        mGetListener = listener;
    }*

    /**
     * Remove the given listener from the list of those that will be notified
     * when logout occurs.
     * 
     * @param listener
     *            The callback object for notifying the application when log out
     *            starts and finishes.
     */
    public static void removePostListener() {
        mPostListener = null;
    }
    
   /* public static void removeGetListener() {
        mGetListener = null;
    }*/
    
    public static void removeLogoutListener(){
    	mLogoutListener = null;
    }
    
    public static void removeAllListeners() {
        mAuthListener = null;
    	mPostListener = null;
    //	mGetListener = null;
    	mLogoutListener = null;
    }
    
 
    public static void onLoginSuccess() {
    	mAuthListener.onAuthSucceed();
    }

    public static void onLoginError(String error) {
        mAuthListener.onAuthFail(error);
    }

    public static void onPostSucceed() {
    	mPostListener.onPostSucceed();
    }

    public static void onPostFail() {
        mPostListener.onPostFail();
    }
    
    public static void onPostError() {
        mPostListener.onPostError();
    }
    
    /* static void onGetSucceed(Bundle data) {
    	mGetListener.onGetSucceed(data);
    }

    public static void onGetFail() {
    	mGetListener.onGetFail();
    }
    
    public static void onGetError() {
        mGetListener.onGetError();
    }*/
    public static void onLogoutBegin(){
    	mLogoutListener.onLogoutBegin();
    }
    public static void onLogoutFinish(){
    	mLogoutListener.onLogoutFinish();
    }
    

    /**
     * Callback interface for authorization events.
     */
    public static interface AuthListener {

        /**
         * Called when a auth flow completes successfully and a valid OAuth
         * Token was received. Executed by the thread that initiated the
         * authentication. API requests can now be made.
         */
        public void onAuthSucceed();

        /**
         * Called when a login completes unsuccessfully with an error.
         * 
         * Executed by the thread that initiated the authentication.
         */
        public void onAuthFail(String error);
    }

    /**
     * Callback interface for logout events.
     */
    public static interface PostListener {
        /**
         * Called when logout begins, before session is invalidated. Last chance
         * to make an API call. Executed by the thread that initiated the
         * logout.
         */
        public void onPostSucceed();

        /**
         * Called when the session information has been cleared. UI should be
         * updated to reflect logged-out state.
         * 
         * Executed by the thread that initiated the logout.
         */
        public void onPostFail();
        public void onPostError();
        
    }
    public static interface LogoutListener {
        /**
         * Called when logout begins, before session is invalidated.  
         * Last chance to make an API call.  
         * 
         * Executed by the thread that initiated the logout.
         */
        public void onLogoutBegin();

        /**
         * Called when the session information has been cleared.
         * UI should be updated to reflect logged-out state.
         * 
         * Executed by the thread that initiated the logout.
         */
        public void onLogoutFinish();
    }
    
  /* public static interface GetListener {
      **
         * Called when logout begins, before session is invalidated. Last chance
         * to make an API call. Executed by the thread that initiated the
         * logout.
         *
        public void onGetSucceed(Bundle data);

       **
         * Called when the session information has been cleared. UI should be
         * updated to reflect logged-out state.
         * 
         * Executed by the thread that initiated the logout.
         *
        public void onGetFail();
        public void onGetError();
        
    }*/

}
