package org.crpt.tasks.durka.services;

import io.kubernetes.client.PodLogs;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreApi;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.KubeConfig;
import io.kubernetes.client.util.Streams;
import org.crpt.tasks.durka.configs.LogsConfig;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class LogsService {
    LogsConfig logsConfig = new LogsConfig();


   public String getLogs(){
        ApiClient apiClient = logsConfig.getClient();

       CoreV1Api coreV1Api = new CoreV1Api(apiClient);

       PodLogs podLogs = new PodLogs();
       V1Pod pod  = null;
       try {
           pod = coreV1Api
                   .listNamespacedPod("gismt-lt","true",null,null,null,"app=org-info",200,null,null,30,false)
                   .getItems()
                   .get(0);
           InputStream is = podLogs.streamNamespacedPodLog(pod);
           File file = new File("src/main/resources/logs/logs.txt");
           try (FileOutputStream outputStream = new FileOutputStream(file)) {
               int read;
               byte[] bytes = new byte[1024];

               while ((read = is.read(bytes)) != -1) {
                   outputStream.write(bytes, 0, read);
               }
           }
           Streams.copy(is,System.out);
           is.close();

       } catch (Exception e) {
           e.printStackTrace();
       }


        return "Logs download";
   }

}
