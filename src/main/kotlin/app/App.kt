package app

import org.junit.Test
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.Writer
import java.util.*

/**
 * Created by Riz
 */
fun writeFiles(numberOfFiles: Int, numberOfLinesPerFile: Int, outputDir: String, outputStr: String, bufferSize:Int= 8192){
  val startTime = Date().time
  val outStreams =  HashMap<Int, Writer>()

  //create the output streams
  for (i in 0..numberOfFiles-1) {
    val filepath = "${outputDir}${File.separator}${i}.csv";
    val writer = BufferedWriter(FileWriter(filepath, true), bufferSize)
    writer.buffered()
    outStreams.put(i, writer)
  }

  //write number of files consecutively
  for(line in 0..numberOfLinesPerFile-1){
    for(stream in 0 .. numberOfFiles) {
      outStreams[stream]?.write(outputStr)
    }
  }

  //close all writers
  outStreams.forEach { i, writer ->  writer.close() }
  val delta = Date().time - startTime
  print("Time elapsed: ${delta} ms")
}

fun main(args: Array<String>){
  if(args.size < 5){
    print("""
    Usage java -jar filewriter [numberOfFiles] [bufferSize(8192)] [numberOfLinesPerFile] [outputDirPath] [stringToWrite]
    """.trim())
    return
  }
  writeFiles(
    numberOfFiles = Integer.parseInt(args[0]),
    numberOfLinesPerFile = Integer.parseInt(args[1]),
    outputDir = args[2],
    outputStr = args[3]
  )
}

class UnitTest{
  @Test
  fun testMain(){
    var args = arrayOf(
      "10",
      "100000",
      "8192",
      "/opt/Development/kotlin/FileWriteBenchmark/outputDir",
      "dskljahkjfs;djf;sdfhdsfh;dshfdklhkasdhfldhskfhdshfkhsdfkhsdfkhskdfhksdhkfdhs\n")
    main(args)
  }

  @Test
  fun testWriteFile(){
    writeFiles(
      numberOfFiles = 10,
      numberOfLinesPerFile = 10,
      outputDir = "/opt/Development/kotlin/FileWriteBenchmark/outputDir",
      outputStr = "dskljahkjfs;djf;sdfhdsfh;dshfdklhkasdhfldhskfhdshfkhsdfkhsdfkhskdfhksdhkfdhs\n"
    )
  }
}