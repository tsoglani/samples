//http://www.codejava.net/coding/capture-and-record-sound-into-wav-file-with-java-sound-api
//http://stackoverflow.com/questions/25798200/java-record-mic-to-byte-array-and-play-sound  

  void capture() {
   
try { 
    AudioFormat format = new AudioFormat(8000.0f, 16, 1, true, true);
        TargetDataLine microphone;
//        SourceDataLine speakers;
 
        microphone = AudioSystem.getTargetDataLine(format);

        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
        microphone = (TargetDataLine) AudioSystem.getLine(info);
        microphone.open(format);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int numBytesRead;
        int CHUNK_SIZE = 1024;
        byte[] data = new byte[microphone.getBufferSize() / 5];
        microphone.start();

        
        
        

        int bytesRead = 0;
        DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
//        speakers = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
//        speakers.open(format);
//        speakers.start();
        while (bytesRead < 100000) {
            numBytesRead = microphone.read(data, 0, CHUNK_SIZE);
            bytesRead += numBytesRead;
            
            
            
            /// check if soumeone is talking ...
            //if is talking       out.write(data, 0, numBytesRead); 
 // if not talking save to file 
            
            // write the mic data to a stream for use later
            out.write(data, 0, numBytesRead); 
            // write mic data to stream for immediate playback
//            speakers.write(data, 0, numBytesRead);
        }
//        speakers.drain();
//        speakers.close();
        microphone.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    }


 private void isSpeechActive() {

        new Thread() {
            public void run() {
                buffer = new byte[mic.getTargetDataLine().getBufferSize() / 5];
                int numBytesRead;

                float[] samples = new float[buffer.length / 2];
                for (int b; (b = mic.getTargetDataLine().read(buffer, 0, buffer.length)) > -1;) {
                    float lastPeak = 0f;
                    // convert bytes to samples here
                    for (int i = 0, s = 0; i < b;) {
                        int sample = 0;

                        sample |= buffer[i++] & 0xFF; // (reverse these two lines
                        sample |= buffer[i++] << 8;   //  if the format is big endian)

                        // normalize to range of +/-1.0f
                        samples[s++] = sample / 32768f;
                    }

                    float rms = 0f;
                    float peak = 0f;
                    for (float sample : samples) {

                        float abs = Math.abs(sample);
                        if (abs > peak) {
                            peak = abs;
                        }

                        rms += sample * sample;
                    }

                    rms = (float) Math.sqrt(rms / samples.length);

                    if (lastPeak > peak) {
                        peak = lastPeak * 0.875f;
                    }

                    lastPeak = peak;
                    final float rms2 = rms, peak2 = peak;
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                      boolean b=      setMeterOnEDT(rms2, peak2);
                            if (b) {
                                System.out.println("start Speech ");
                            }
                        }
                    });
                }
            }
        }.start();

    }
    public byte[] buffer;
    int CHUNK_SIZE = 1024;

    public void isSpeechActive2() {
        buffer = new byte[mic.getTargetDataLine().getBufferSize() / 5];
        int numBytesRead;
        while (true) {
            int bytesRead = 0;

            while (bytesRead < CHUNK_SIZE * 2) { // Just so I can test if recording
                // my mic works...
                numBytesRead = mic.getTargetDataLine().read(buffer, 0, CHUNK_SIZE);
                bytesRead = bytesRead + numBytesRead;
            }

            short max;
            boolean startSpeech = exxx(buffer.length, buffer);
            if (startSpeech) {
                System.out.println("start Speech");
            }

            bytesRead = 0;
        }
    }

    private boolean exxx(int bufferSizeInBytes, byte[] audioBuffer) {
//        int numberOfReadBytes = bufferSizeInBytes;
        float tempFloatBuffer[] = new float[3];
        int tempIndex = 0;
        float totalAbsValue = 0.0f;

        for (int i = 0; i < bufferSizeInBytes; i += 2) {
            short sample = 0;

            sample = (short) ((audioBuffer[i]) | audioBuffer[i + 1] << 8);
            totalAbsValue += Math.abs(sample) / (bufferSizeInBytes / 2);
        }

        // Analyze temp buffer.
        tempFloatBuffer[tempIndex % 3] = totalAbsValue;
        float temp = 0.0f;
        for (int i = 0; i < 3; ++i) {
            temp += tempFloatBuffer[i];
        }

        if ((temp >= 0 && temp <= 350)) {
//            tempIndex++;
            return false;
        }

        if (temp > 350) {
            return true;
        }

        return false;
    }
    private int meterWidth = 10;

    private float amp = 0f;
    private float peak = 0f;

    boolean setMeterOnEDT(final float rms, final float peak) {

        amp = (rms);
        Jarvis.this.peak = peak;
//        System.out.println(rms + "  " + peak);
        if (peak > 0.1 && rms > 0.1) {
            return true;
        } else {

            return false;
        }

    }

