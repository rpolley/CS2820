package production;

/**
 * 
 * @author Ted Herman.
 * 
 * Methods provided by the Belt component.
 *
 */
public interface Belt {
  boolean binAvailable();  // true if Picker can get a new Bin
  Bin getBin();  // called by Orders when Picker wants a new Bin 
  }

