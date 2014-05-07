package DPIS_idl;

/** 
 * Helper class for : DPIS
 *  
 * @author OpenORB Compiler
 */ 
public class DPISHelper
{
    /**
     * Insert DPIS into an any
     * @param a an any
     * @param t DPIS value
     */
    public static void insert(org.omg.CORBA.Any a, DPIS_idl.DPIS t)
    {
        a.insert_Object(t , type());
    }

    /**
     * Extract DPIS from an any
     *
     * @param a an any
     * @return the extracted DPIS value
     */
    public static DPIS_idl.DPIS extract( org.omg.CORBA.Any a )
    {
        if ( !a.type().equivalent( type() ) )
        {
            throw new org.omg.CORBA.MARSHAL();
        }
        try
        {
            return DPIS_idl.DPISHelper.narrow( a.extract_Object() );
        }
        catch ( final org.omg.CORBA.BAD_PARAM e )
        {
            throw new org.omg.CORBA.MARSHAL(e.getMessage());
        }
    }

    //
    // Internal TypeCode value
    //
    private static org.omg.CORBA.TypeCode _tc = null;

    /**
     * Return the DPIS TypeCode
     * @return a TypeCode
     */
    public static org.omg.CORBA.TypeCode type()
    {
        if (_tc == null) {
            org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init();
            _tc = orb.create_interface_tc( id(), "DPIS" );
        }
        return _tc;
    }

    /**
     * Return the DPIS IDL ID
     * @return an ID
     */
    public static String id()
    {
        return _id;
    }

    private final static String _id = "IDL:DPIS_idl/DPIS:1.0";

    /**
     * Read DPIS from a marshalled stream
     * @param istream the input stream
     * @return the readed DPIS value
     */
    public static DPIS_idl.DPIS read(org.omg.CORBA.portable.InputStream istream)
    {
        return(DPIS_idl.DPIS)istream.read_Object(DPIS_idl._DPISStub.class);
    }

    /**
     * Write DPIS into a marshalled stream
     * @param ostream the output stream
     * @param value DPIS value
     */
    public static void write(org.omg.CORBA.portable.OutputStream ostream, DPIS_idl.DPIS value)
    {
        ostream.write_Object((org.omg.CORBA.portable.ObjectImpl)value);
    }

    /**
     * Narrow CORBA::Object to DPIS
     * @param obj the CORBA Object
     * @return DPIS Object
     */
    public static DPIS narrow(org.omg.CORBA.Object obj)
    {
        if (obj == null)
            return null;
        if (obj instanceof DPIS)
            return (DPIS)obj;

        if (obj._is_a(id()))
        {
            _DPISStub stub = new _DPISStub();
            stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
            return stub;
        }

        throw new org.omg.CORBA.BAD_PARAM();
    }

    /**
     * Unchecked Narrow CORBA::Object to DPIS
     * @param obj the CORBA Object
     * @return DPIS Object
     */
    public static DPIS unchecked_narrow(org.omg.CORBA.Object obj)
    {
        if (obj == null)
            return null;
        if (obj instanceof DPIS)
            return (DPIS)obj;

        _DPISStub stub = new _DPISStub();
        stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
        return stub;

    }

}
