package pompei.maths.letters.core;

public interface KeyStorage {

  String load(String key);

  void save(String key, String value);

}
