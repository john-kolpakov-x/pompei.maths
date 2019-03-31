package pompei.maths.bml.iface;

import pompei.maths.bml.iface.as.AsBytes;

import java.util.List;

public interface BmlTagList extends List<BmlTag> {
  AsBytes content();
}
