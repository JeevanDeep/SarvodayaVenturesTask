package com.jeevan.sarvodayaventurestask

import android.os.Environment
import com.jeevan.sarvodayaventurestask.model.ContactsModel
import java.io.File
import java.io.FileWriter

class CsvWriter {
    companion object {
        fun saveToCsv(fileName: String, list: MutableList<ContactsModel>) {

            val folder = File("" + Environment.getExternalStorageDirectory() + "/Sarvodya")

            if (!folder.exists())
                folder.mkdir()
            val file = folder.toString() + "/$fileName.csv"


            val fileWriter = FileWriter(file)
            val FILE_HEADER = "name,phoneNumber"
            val COMMA_DELIMITER = ","
            val NEW_LINE_SEPARATOR = "\n"

            fileWriter.append(FILE_HEADER)
            fileWriter.append(NEW_LINE_SEPARATOR)

            for (i in list.indices) {
                fileWriter.append(list[i].name)
                fileWriter.append(COMMA_DELIMITER)
                fileWriter.append(list[i].phoneNumber)
                fileWriter.append(NEW_LINE_SEPARATOR)
            }

            fileWriter.flush()
            fileWriter.close()

        }
    }
}