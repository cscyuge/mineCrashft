from glob import glob
for _ in glob("*.uc"):
    with open(_, 'rb') as f:
        data = list(f.read())
        for i in range(len(data)):
            data[i] = chr(ord(data[i]) ^ 0xa3)
        with open(_[:-3] + ".mp3", 'wb') as f2:
            f2.write(''.join(data))
