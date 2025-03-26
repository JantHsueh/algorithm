/**
 * create by xuexuan
 * time 2020/12/23 3:49 下午
 */

class SingletonDemo private constructor(){


    companion object {
         @Volatile
         var instance: SingletonDemo? = null
            get() {
            if (instance == null) {
                synchronized(this) {
                    if(instance ==null)
                    {
                        instance = SingletonDemo()
                    }
                }
            }
            return instance
        }


        val instance2 : SingletonDemo by lazy(mode= LazyThreadSafetyMode.SYNCHRONIZED) { SingletonDemo() }

    }

}



