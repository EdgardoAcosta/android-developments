package NetworkUtility

import java.util.concurrent.Executors

private val IO_EXECUTOR = Executors.newSingleThreadExecutor()

class Executor {

    companion object {
        fun ioThread(f: () -> Unit) {
            IO_EXECUTOR.execute(f)
        }
    }


}