package ru.nitrodenov.messenger.async

import android.os.AsyncTask

abstract class PendingTask : AsyncTask<TaskParams, TaskProgress, TaskResult>()