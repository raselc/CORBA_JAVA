package DPIS_idl;

/**
 * Interface definition: DPIS.
 * 
 * @author OpenORB Compiler
 */
public class _DPISStub extends org.omg.CORBA.portable.ObjectImpl
        implements DPIS
{
    static final String[] _ids_list =
    {
        "IDL:DPIS_idl/DPIS:1.0"
    };

    public String[] _ids()
    {
     return _ids_list;
    }

    private final static Class _opsClass = DPIS_idl.DPISOperations.class;

    /**
     * Operation createCRecord
     */
    public boolean createCRecord(String firstName, String lastName, String description, String status, String badgeId)
    {
        while(true)
        {
            if (!this._is_local())
            {
                org.omg.CORBA.portable.InputStream _input = null;
                try
                {
                    org.omg.CORBA.portable.OutputStream _output = this._request("createCRecord",true);
                    _output.write_string(firstName);
                    _output.write_string(lastName);
                    _output.write_string(description);
                    _output.write_string(status);
                    _output.write_string(badgeId);
                    _input = this._invoke(_output);
                    boolean _arg_ret = _input.read_boolean();
                    return _arg_ret;
                }
                catch(org.omg.CORBA.portable.RemarshalException _exception)
                {
                    continue;
                }
                catch(org.omg.CORBA.portable.ApplicationException _exception)
                {
                    String _exception_id = _exception.getId();
                    throw new org.omg.CORBA.UNKNOWN("Unexpected User Exception: "+ _exception_id);
                }
                finally
                {
                    this._releaseReply(_input);
                }
            }
            else
            {
                org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke("createCRecord",_opsClass);
                if (_so == null)
                   continue;
                DPIS_idl.DPISOperations _self = (DPIS_idl.DPISOperations) _so.servant;
                try
                {
                    return _self.createCRecord( firstName,  lastName,  description,  status,  badgeId);
                }
                finally
                {
                    _servant_postinvoke(_so);
                }
            }
        }
    }

    /**
     * Operation createMRecord
     */
    public boolean createMRecord(String firstName, String lastName, String address, String lastdate, String lastaddress, String status, String badgeId)
    {
        while(true)
        {
            if (!this._is_local())
            {
                org.omg.CORBA.portable.InputStream _input = null;
                try
                {
                    org.omg.CORBA.portable.OutputStream _output = this._request("createMRecord",true);
                    _output.write_string(firstName);
                    _output.write_string(lastName);
                    _output.write_string(address);
                    _output.write_string(lastdate);
                    _output.write_string(lastaddress);
                    _output.write_string(status);
                    _output.write_string(badgeId);
                    _input = this._invoke(_output);
                    boolean _arg_ret = _input.read_boolean();
                    return _arg_ret;
                }
                catch(org.omg.CORBA.portable.RemarshalException _exception)
                {
                    continue;
                }
                catch(org.omg.CORBA.portable.ApplicationException _exception)
                {
                    String _exception_id = _exception.getId();
                    throw new org.omg.CORBA.UNKNOWN("Unexpected User Exception: "+ _exception_id);
                }
                finally
                {
                    this._releaseReply(_input);
                }
            }
            else
            {
                org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke("createMRecord",_opsClass);
                if (_so == null)
                   continue;
                DPIS_idl.DPISOperations _self = (DPIS_idl.DPISOperations) _so.servant;
                try
                {
                    return _self.createMRecord( firstName,  lastName,  address,  lastdate,  lastaddress,  status,  badgeId);
                }
                finally
                {
                    _servant_postinvoke(_so);
                }
            }
        }
    }

    /**
     * Operation getRecordCounts
     */
    public String getRecordCounts(String badgeId)
    {
        while(true)
        {
            if (!this._is_local())
            {
                org.omg.CORBA.portable.InputStream _input = null;
                try
                {
                    org.omg.CORBA.portable.OutputStream _output = this._request("getRecordCounts",true);
                    _output.write_string(badgeId);
                    _input = this._invoke(_output);
                    String _arg_ret = _input.read_string();
                    return _arg_ret;
                }
                catch(org.omg.CORBA.portable.RemarshalException _exception)
                {
                    continue;
                }
                catch(org.omg.CORBA.portable.ApplicationException _exception)
                {
                    String _exception_id = _exception.getId();
                    throw new org.omg.CORBA.UNKNOWN("Unexpected User Exception: "+ _exception_id);
                }
                finally
                {
                    this._releaseReply(_input);
                }
            }
            else
            {
                org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke("getRecordCounts",_opsClass);
                if (_so == null)
                   continue;
                DPIS_idl.DPISOperations _self = (DPIS_idl.DPISOperations) _so.servant;
                try
                {
                    return _self.getRecordCounts( badgeId);
                }
                finally
                {
                    _servant_postinvoke(_so);
                }
            }
        }
    }

    /**
     * Operation editCRecord
     */
    public boolean editCRecord(String lastName, String recordID, String newStatus, String badgeId)
    {
        while(true)
        {
            if (!this._is_local())
            {
                org.omg.CORBA.portable.InputStream _input = null;
                try
                {
                    org.omg.CORBA.portable.OutputStream _output = this._request("editCRecord",true);
                    _output.write_string(lastName);
                    _output.write_string(recordID);
                    _output.write_string(newStatus);
                    _output.write_string(badgeId);
                    _input = this._invoke(_output);
                    boolean _arg_ret = _input.read_boolean();
                    return _arg_ret;
                }
                catch(org.omg.CORBA.portable.RemarshalException _exception)
                {
                    continue;
                }
                catch(org.omg.CORBA.portable.ApplicationException _exception)
                {
                    String _exception_id = _exception.getId();
                    throw new org.omg.CORBA.UNKNOWN("Unexpected User Exception: "+ _exception_id);
                }
                finally
                {
                    this._releaseReply(_input);
                }
            }
            else
            {
                org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke("editCRecord",_opsClass);
                if (_so == null)
                   continue;
                DPIS_idl.DPISOperations _self = (DPIS_idl.DPISOperations) _so.servant;
                try
                {
                    return _self.editCRecord( lastName,  recordID,  newStatus,  badgeId);
                }
                finally
                {
                    _servant_postinvoke(_so);
                }
            }
        }
    }

    /**
     * Operation transferRecord
     */
    public boolean transferRecord(String recordID, String bagdeId, String remoteStation)
    {
        while(true)
        {
            if (!this._is_local())
            {
                org.omg.CORBA.portable.InputStream _input = null;
                try
                {
                    org.omg.CORBA.portable.OutputStream _output = this._request("transferRecord",true);
                    _output.write_string(recordID);
                    _output.write_string(bagdeId);
                    _output.write_string(remoteStation);
                    _input = this._invoke(_output);
                    boolean _arg_ret = _input.read_boolean();
                    return _arg_ret;
                }
                catch(org.omg.CORBA.portable.RemarshalException _exception)
                {
                    continue;
                }
                catch(org.omg.CORBA.portable.ApplicationException _exception)
                {
                    String _exception_id = _exception.getId();
                    throw new org.omg.CORBA.UNKNOWN("Unexpected User Exception: "+ _exception_id);
                }
                finally
                {
                    this._releaseReply(_input);
                }
            }
            else
            {
                org.omg.CORBA.portable.ServantObject _so = _servant_preinvoke("transferRecord",_opsClass);
                if (_so == null)
                   continue;
                DPIS_idl.DPISOperations _self = (DPIS_idl.DPISOperations) _so.servant;
                try
                {
                    return _self.transferRecord( recordID,  bagdeId,  remoteStation);
                }
                finally
                {
                    _servant_postinvoke(_so);
                }
            }
        }
    }

}
