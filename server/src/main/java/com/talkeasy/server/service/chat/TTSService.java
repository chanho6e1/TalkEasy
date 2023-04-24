package com.talkeasy.server.service.chat;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.texttospeech.v1.*;
import com.google.protobuf.ByteString;

import com.talkeasy.server.config.s3.S3Uploader;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.UUID;

import javax.sound.sampled.UnsupportedAudioFileException;


@Service
@RequiredArgsConstructor
public class TTSService {

    private final ResourceLoader resourceLoader;
    private final S3Uploader s3Uploader;


    public String getTTS(String text) throws IOException, UnsupportedAudioFileException {

        Resource resource = resourceLoader.getResource("classpath:talkeasy-384300-3d129ed9d54d.json");

        GoogleCredentials credentials = GoogleCredentials.fromStream(resource.getInputStream());
        TextToSpeechSettings settings = TextToSpeechSettings.newBuilder()
                .setCredentialsProvider(FixedCredentialsProvider.create(credentials)).build();

        try (TextToSpeechClient textToSpeechClient = TextToSpeechClient.create(settings)) {
            // Set the text input to be synthesized
            SynthesisInput input = SynthesisInput.newBuilder().setText(text).build();

            // Build the voice request, select the language code ("en-US") and the ssml voice gender
            // ("neutral")
            VoiceSelectionParams voice =
                    VoiceSelectionParams.newBuilder()
                            .setLanguageCode("ko-KR")
                            .setSsmlGender(SsmlVoiceGender.NEUTRAL)
                            .build();

            // Select the type of audio file you want returned
            AudioConfig audioConfig =
                    AudioConfig.newBuilder().setAudioEncoding(AudioEncoding.MP3).build();

            // Perform the text-to-speech request on the text input with the selected voice parameters and
            // audio file type
            SynthesizeSpeechResponse response =
                    textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);

            // Get the audio contents from the response
            ByteString audioContents = response.getAudioContent();

            // Convert ByteString to byte array
            byte[] audioBytes = audioContents.toByteArray();
            return uploadAudioToS3(audioBytes);
        }
    }


    public String uploadAudioToS3(byte[] audioBytes) throws IOException {

        String originalFileName = "temp.mp3";
        MultipartFile multipartFile = new MockMultipartFile(originalFileName, originalFileName,
                "audio/mp3", audioBytes);

        // Upload the MP3 file to S3 and get the URL
        String s3Key = "static/tts/" + UUID.randomUUID().toString() + ".mp3";
        String s3Url = s3Uploader.uploadFiles(multipartFile, s3Key);

        return s3Url;
    }


}