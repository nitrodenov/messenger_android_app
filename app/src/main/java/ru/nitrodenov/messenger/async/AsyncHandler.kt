package ru.nitrodenov.messenger.async

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ExecutorService

interface AsyncHandler {

    fun submit(id: String, task: PendingTask)

    fun stopTask(id: String)

    fun removeTask(id: String)

    fun stopAllTasks()

    fun getTask(id: String): PendingTask?

}

class AsyncHandlerImpl(private val executorService: ExecutorService) : AsyncHandler {

    private val tasks = ConcurrentHashMap<String, PendingTask>()

    override fun submit(id: String, task: PendingTask) {
        tasks.put(id, task)
        task.executeOnExecutor(executorService)
    }

    override fun stopTask(id: String) {
        val task = tasks[id]
        task?.cancel(true)
        tasks.remove(id)
    }

    override fun stopAllTasks() {
        tasks.forEach { it.value.cancel(true) }
        tasks.clear()
    }

    override fun removeTask(id: String) {
        tasks.remove(id)
    }

    override fun getTask(id: String): PendingTask? {
        return tasks[id]
    }

}