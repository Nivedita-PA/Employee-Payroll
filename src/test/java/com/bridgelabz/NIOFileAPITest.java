package com.bridgelabz;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.IntStream;

public class NIOFileAPITest {
    
    private static String HOME = System.getProperty("E:\\Employee-Payroll\\src\\main\\resources");
    private static String PLAY_WITH_NIO = System.getProperty("TempPlayGround");

    @Test
    public void givenPathWhenCheckedThenConfirm() throws IOException {
        //checking file exits
        Path homePath = Paths.get(HOME);
        Assert.assertTrue(Files.exists(homePath));

        //Deleting and checking file not exits
        Path playPath = Paths.get(HOME + "/" + PLAY_WITH_NIO);
        if(Files.exists(playPath)) FileUtils.deleteFiles(playPath.toFile());
        Assert.assertTrue(Files.notExists(playPath));

        //Creating file
        IntStream.range(1,10).forEach(cntr->{
            Path tempFile = Paths.get(playPath + "/temp" + cntr);
            Assert.assertTrue(Files.notExists(tempFile));
            try { Files.createFile(tempFile);}
            catch (IOException e) { }
            Assert.assertTrue(Files.exists(tempFile));
        });

        //List files, dir, as well as files with extension
        Files.list(playPath).filter(Files::isRegularFile).forEach(System.out::println);
        Files.newDirectoryStream(playPath).forEach(System.out::println);
        Files.newDirectoryStream(playPath, path-> path.toFile().isFile() &&
                                                   path.toString().startsWith("temp"))
                .forEach(System.out::println);
    }
}
