package com.example.callrecordingapp

import android.media.MediaRecorder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.widget.Button
import java.io.File

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val start = findViewById<Button>(R.id.start)
        val stop = findViewById<Button>(R.id.end)
        val recording = findViewById<Button>(R.id.list)

        start.setOnClickListener {
            startRecording()
        }

        stop.setOnClickListener {
            stopRecording()

        }
        recording.setOnClickListener {
            getOutputFile()
        }

        class CallStateListener : PhoneStateListener() {
            override fun onCallStateChanged(state: Int, phoneNumber: String?) {
                super.onCallStateChanged(state, phoneNumber)
                when (state) {
                    TelephonyManager.CALL_STATE_RINGING -> {
                        // Incoming call
                    }
                    TelephonyManager.CALL_STATE_OFFHOOK -> {
                        // Call is in progress
                        startRecording()
                    }
                    TelephonyManager.CALL_STATE_IDLE -> {
                        // Call has ended
                        stopRecording()
                    }
                }
            }

            private lateinit var mediaRecorder: MediaRecorder
            private var isRecording = false
            private fun startRecording() {
                // Initialize the MediaRecorder
                mediaRecorder = MediaRecorder()
                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                val outputFile = getOutputFile()
                mediaRecorder.setOutputFile(outputFile)

                try {
                    mediaRecorder.prepare()
                    mediaRecorder.start()
                    isRecording = true
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            private fun stopRecording() {
                if (isRecording) {
                    mediaRecorder.stop()
                    mediaRecorder.release()
                    isRecording = false
                }
            }

            private fun getOutputFile(): String {
                // Define the file path and name
                val dir = Environment.getExternalStorageDirectory()
                val folder = File(dir, "Krishan Recording")
                if (!folder.exists()) {
                    folder.mkdirs()
                }
                return "${folder.absolutePath}/recording_${System.currentTimeMillis()}.3gp"
            }
        }

    }

    private fun getOutputFile(): String {
        // Define the file path and name
        val dir = Environment.getExternalStorageDirectory()
        val folder = File(dir, "Krishan Recording")
        if (!folder.exists()) {
            folder.mkdirs()
        }
        return "${folder.absolutePath}/recording_${System.currentTimeMillis()}.3gp"
    }

    private fun stopRecording() {
        if (isRecording) {
            mediaRecorder.stop()
            mediaRecorder.release()
            isRecording = false
        }
    }

    private lateinit var mediaRecorder: MediaRecorder
    private var isRecording = false
    private fun startRecording() {
        mediaRecorder = MediaRecorder()
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        val outputFile = getOutputFile()
        mediaRecorder.setOutputFile(outputFile)
        try {
            mediaRecorder.prepare()
            mediaRecorder.start()
            isRecording = true
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}