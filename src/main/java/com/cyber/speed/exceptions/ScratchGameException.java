package com.cyber.speed.exceptions;


public class ScratchGameException extends RuntimeException {

   /**
    * Default constructor with no message or cause.
    */
   public ScratchGameException() {
      super();
   }

   /**
    * Constructor with a custom error message.
    *
    * @param message The custom error message.
    */
   public ScratchGameException(String message) {
      super(message);
   }

   /**
    * Constructor with a custom error message and a cause.
    *
    * @param message The custom error message.
    * @param cause   The throwable cause of this exception.
    */
   public ScratchGameException(String message, Throwable cause) {
      super(message, cause);
   }

   /**
    * Constructor with a cause.
    *
    * @param cause The throwable cause of this exception.
    */
   public ScratchGameException(Throwable cause) {
      super(cause);
   }
}
