package pompei.maths.bml.iface;

import pompei.maths.bml.iface.as.AsByte;
import pompei.maths.bml.iface.as.AsBytes;
import pompei.maths.bml.iface.as.AsDouble;
import pompei.maths.bml.iface.as.AsFloat;
import pompei.maths.bml.iface.as.AsInt;
import pompei.maths.bml.iface.as.AsLong;
import pompei.maths.bml.iface.as.AsStr;

public interface BmlTagHeaderValue {

  ValueType type();

  void setType(ValueType type);

  int sizeInBytes();

  void setSizeInBytes(int sizeInBytes);

  AsByte asByte();

  AsBytes asBytes();

  AsFloat asFloat();

  AsDouble asDouble();

  AsInt asInt();

  AsLong asLong();

  AsStr asStr();

}
