
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;

namespace ConsoleApp1
{
    class RC4
    {
        private static int SBOX_LENGTH = 256;
        private byte[] key = new byte[SBOX_LENGTH - 1];
        private int[] sbox = new int[SBOX_LENGTH];

        public RC4()
        {
            Reset();
        }
        private void Reset()
        {
            for (int i = 0; i < key.Length; i++)
            {
                key[i] = (byte)0;
            }
            for (int i = 0; i < sbox.Length; i++)
            {
                sbox[i] = 0;
            }

        }
        //加密
        public byte[] encryptMessage(String message, String key)
        {
            Reset();
            setKey(key);
            byte[] crypt = Crypt(Encoding.UTF8.GetBytes(message));
            Reset();
            return crypt;
        }

        public byte[] encrypt(byte[] message, String key)
        {
            Reset();
            setKey(key);
            byte[] crypt = Crypt(message);
            Reset();
            return crypt;
        }
        //解密
        public String decryptMessage(byte[] message, String key)

        {
            Reset();
            setKey(key);
            byte[] msg = Crypt(message);
            Reset();
            return Encoding.UTF8.GetString(msg);
        }

        public byte[] Crypt(byte[] msg)
        {
            sbox = initSBox(key);
            byte[] code = new byte[msg.Length];
            int i = 0;
            int j = 0;
            for (int n = 0; n < msg.Length; n++)
            {
                i = (i + 1) % SBOX_LENGTH;
                j = (j + sbox[i]) % SBOX_LENGTH;
                swap(i, j, sbox);
                int rand = sbox[(sbox[i] + sbox[j]) % SBOX_LENGTH];
                code[n] = (byte)(rand ^ msg[n]);
            }
            return code;
        }

        private int[] initSBox(byte[] key)
        {
            int[] sbox = new int[SBOX_LENGTH];
            int j = 0;

            for (int i = 0; i < SBOX_LENGTH; i++)
            {
                sbox[i] = i;
            }

            for (int i = 0; i < SBOX_LENGTH; i++)
            {
                j = (j + sbox[i] + (key[i % key.Length]) & 0xFF) % SBOX_LENGTH;
                swap(i, j, sbox);
            }
            return sbox;
        }

        private void swap(int i, int j, int[] sbox)
        {
            int temp = sbox[i];
            sbox[i] = sbox[j];
            sbox[j] = temp;
        }

        public void setKey(String key)
        {
            this.key = Encoding.UTF8.GetBytes(key);
        }
    }
}