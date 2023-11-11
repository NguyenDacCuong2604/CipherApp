package model.Hash;

import java.io.File;

public interface IHash {
    public void instance(String method);
    public String hashFile(File file);
    public String hashText(String plainText);
}
