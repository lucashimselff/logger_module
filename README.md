Code Design:
- write() method:
  create a new thread for every writing tasks to ensure asynchronous writing.

- stop() method:
  When waitForCompletion = false, stop the wrting task immediately. When waitForCompletion = true, wait until write(), who has the current lock, to finish executing first, and then stop.


Unit Test Cases:     
- #1 testWritingToLog():    
  Test whether a call can write logs into the file

- #2 testNewFilesCreatedOnMidnightCross():
  Test whether New files are created if midnight is crossed. Please note that this test case can only be execute when the local time cross the midnight. It cannot run successfully during other time of the day.

- #3 testImmediateStopBehavior():     
  Test whether writing would be stopped immediately if waitForCompletion is set to be false.

- #4 testWaitForCompletionStopBehavior():
  Test whether writing is stopped after current message has finished writing. (A long message is used to for a longer writing time)

