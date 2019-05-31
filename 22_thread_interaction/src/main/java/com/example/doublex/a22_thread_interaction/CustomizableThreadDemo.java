package com.example.doublex.a22_thread_interaction;

class CustomizableThreadDemo implements TestDemo{
    private CustomizableThread thread = new CustomizableThread();

    @Override
    public void runTest() {
        thread.start();
    }


    class CustomizableThread extends  Thread{
        @Override
        public void run() {
            while (true){

            }
        }
    }
}
