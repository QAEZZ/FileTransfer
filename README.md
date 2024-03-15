# FileTransfer

Decided to learn the basics of Java.
Simple file transfer program inspired by SCP using TCP; I might make my own protocol when I get more familiar with the lang.

Do NOT actually use it, it's not secure; this is for fun.

### Info
- openjdk 21
- maven
- runs on port 1122

**To build and run:** \
`cd` into the project root and run `mvn clean install`. \
Then `cd` into `target` and run `java -jar FileTransfer-1.0.jar <args>`

Alternatively, you can use the `.jar` in `out/`.

### Usage

**To send files:**
```sh
$ ft send file.txt ip:~/target/path
```
<br/>

**To recieve files:** \
The `--overwrite` flag overwrites the local file with the recieved file if it exists. Otherwise it will let the user know that there is a conflict and won't do anything.
```sh
$ ft listen [--overwrite]
```