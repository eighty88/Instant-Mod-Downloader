# Instant Mod Downloader
MinecraftのModを簡単にインストールできるように作成した
プログラムです。

## How To Build
 1. [Click Here][zip] よりZipファイルをダウンロードします。
 2. ./modpack.jsonの中身を変更し、web上にアップロードしてください。
 3. ./src/main/resources/config.jsonを開き、updateJsonを書き換えます。
 4. 同梱されているGradle Wrapperでビルドを行います。
  - Windowsの場合
 gradlew build
  - Linuxの場合
 ./gradlew build

 [zip]:https://github.com/eighty88/Instant-Mod-Downloader/archive/main.zip