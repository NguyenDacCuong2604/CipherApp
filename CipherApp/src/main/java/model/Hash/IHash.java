package model.Hash;

public interface IHash {
    public void instance(String method);
    public String hashFile(String pathSource);
    public String hashText(String plainText);
}
