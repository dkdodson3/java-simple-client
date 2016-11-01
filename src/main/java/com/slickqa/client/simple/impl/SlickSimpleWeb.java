package com.slickqa.client.simple.impl;


import com.slickqa.client.simple.definitions.SlickFileMetadata;
import com.slickqa.client.simple.definitions.TestRun;
import org.glassfish.jersey.client.JerseyClientBuilder;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Keith on 10/26/16.
 */
public class SlickSimpleWeb {
    private static Client client = null;

    private static WebTarget getTarget() {
        if (client == null) {
            client = JerseyClientBuilder.newClient();
        }

        String url = "/api/simple";

        return client.target(url);
    }


//    public static SlickFileMetadata getFileId(String resultId, SlickFileMetadata data) {
//        WebTarget target = getTarget().path("/api/simple/result/" + resultId + "/create_file");
//        Entity entityData = Entity.entity(data, MediaType.APPLICATION_JSON);
//        Response response = target.request(MediaType.APPLICATION_JSON).post(entityData);
//
//        return response.readEntity(SlickFileMetadata.class);
//    }

//    public static void putFile(String file_id, SlickFile slickFile) {
//        WebTarget target = getTarget().path("/api/simple/result/" + slickFile.getResultId() + "/" + file_id);
//        client.setChunkedEncodingSize(1024);
//        File file = new File(slickFile.getFilePath());
//        InputStream fileInStream = new FileInputStream(file);
//        String contentDisposition = "attachment; filename=\"" + file.getName() + "\"";
//        ClientResponse response = rootResource.path("attachment").path("upload").path("your-file-name")
//                .type(MediaType.APPLICATION_OCTET_STREAM).header("Content-Disposition", contentDisposition)
//                .post(ClientResponse.class, fileInStream);
//
//
//        HttpEntity entity = MultipartEntityBuilder.create().addTextBody("field1", "value1").addBinaryBody().build();
//
//    }


}
