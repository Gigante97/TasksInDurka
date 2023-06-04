package org.crpt.tasks.durka.configs;

import io.kubernetes.client.openapi.ApiClient;

import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.KubeConfig;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
@Component
public class LogsConfig {

    public ApiClient getClient(){
        String kubeConfigPath = "src/main/resources/kubeconfigs/kubeconfig.yaml";


        {
            try {
                ApiClient client = ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader(kubeConfigPath))).build();
                Configuration.setDefaultApiClient(client);
                return client;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new ApiClient();

    }


}
