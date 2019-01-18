class MessageQueue
{
  public LinkedList queue;

  public MessageQueue()
  {
    queue = new LinkedList();
  }

  synchronized public void put(Object value)
  {
    queue.addLast(value);
    notifyAll();
  }

  synchronized public Object get()
  {
    while (queue.isEmpty ()) {
      try {
        wait();
      }
      catch (InterruptedException e) {
      }
    }
    return queue.removeFirst();
  }
}
