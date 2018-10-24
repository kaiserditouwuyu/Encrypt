using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Security.Cryptography;
using System.Text;

/// <summary>
/// MD5Hash 的摘要说明
/// </summary>
public class MD5Hash
{
    public static String GetMD5Password(String password) {
        MD5CryptoServiceProvider md5Hash = new MD5CryptoServiceProvider();
        byte[] data = md5Hash.ComputeHash(Encoding.Default.GetBytes(password));
        StringBuilder sb = new StringBuilder();
        foreach (byte b in data)
        {
            sb.Append(b.ToString("x2"));
        }
        return sb.ToString();
    }

    //HMAC-MD5
    public static string ToHMACMD5(string encryptText, string encryptKey)
    {

        byte[] key = Encoding.UTF8.GetBytes(encryptKey);
        HMACMD5 hmac = new HMACMD5(key);
        byte[] value = hmac.ComputeHash(Encoding.UTF8.GetBytes(encryptText));
        return HexMethod.ToHex(value);

    }
}