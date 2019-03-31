package pompei.maths.bml.iface;

import java.util.UUID;

public interface BmlTag {

  UUID id();

  void setId(UUID id);

  BmlTagHeader header();

  BmlTagList children();

}
