package DPIS_idl;

/**
 * Holder class for : DPIS
 * 
 * @author OpenORB Compiler
 */
final public class DPISHolder
        implements org.omg.CORBA.portable.Streamable
{
    /**
     * Internal DPIS value
     */
    public DPIS_idl.DPIS value;

    /**
     * Default constructor
     */
    public DPISHolder()
    { }

    /**
     * Constructor with value initialisation
     * @param initial the initial value
     */
    public DPISHolder(DPIS_idl.DPIS initial)
    {
        value = initial;
    }

    /**
     * Read DPIS from a marshalled stream
     * @param istream the input stream
     */
    public void _read(org.omg.CORBA.portable.InputStream istream)
    {
        value = DPISHelper.read(istream);
    }

    /**
     * Write DPIS into a marshalled stream
     * @param ostream the output stream
     */
    public void _write(org.omg.CORBA.portable.OutputStream ostream)
    {
        DPISHelper.write(ostream,value);
    }

    /**
     * Return the DPIS TypeCode
     * @return a TypeCode
     */
    public org.omg.CORBA.TypeCode _type()
    {
        return DPISHelper.type();
    }

}
