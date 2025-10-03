package com.demo.config;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.agent.model.NewService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import java.net.InetAddress;
import java.util.UUID;

public class ConsulConfig {
    private ConsulClient consulClient;
    private String registeredServiceId;

    @PostConstruct
    public void registerService() {
        String consulHost = System.getenv().getOrDefault("CONSUL_HOST", "consul");
        int consulPort = Integer.parseInt(System.getenv().getOrDefault("CONSUL_PORT", "8500"));

        consulClient = new ConsulClient(consulHost, consulPort);

        String serviceName = System.getenv().getOrDefault("SERVICE_NAME", "book-service");
        String serviceId = System.getenv().get("SERVICE_ID"); // nếu set ở env thì dùng
        String servicePortStr = System.getenv().getOrDefault("SERVICE_PORT", "8080");
        int servicePort = Integer.parseInt(servicePortStr);

        if (serviceId == null || serviceId.isBlank()) {
            // tạo id duy nhất (ngắn gọn)
            serviceId = serviceName + "-" + UUID.randomUUID().toString().substring(0, 8);
        }

        String serviceAddress;
        try {
            serviceAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            serviceAddress = System.getenv().getOrDefault("SERVICE_ADDRESS", "127.0.0.1");
        }

        NewService service = new NewService();
        service.setId(serviceId);
        service.setName(serviceName);
        service.setAddress(serviceAddress);
        service.setPort(servicePort);

        NewService.Check check = new NewService.Check();
        check.setHttp("http://" + serviceAddress + ":" + servicePort + "/BookServices/health");
        check.setInterval("10s");
        check.setDeregisterCriticalServiceAfter("1m"); // nếu health fail 1 phút thì tự remove
        service.setCheck(check);

        consulClient.agentServiceRegister(service);
        registeredServiceId = serviceId;

        System.out.println("✅ Registered service to Consul: id=" + serviceId + " address=" + serviceAddress + ":" + servicePort);
    }

    @PreDestroy
    public void deregister() {
        try {
            if (consulClient != null && registeredServiceId != null) {
                consulClient.agentServiceDeregister(registeredServiceId);
                System.out.println("✅ Deregistered service from Consul: " + registeredServiceId);
            }
        } catch (Exception e) {
            System.err.println("Error deregistering from Consul: " + e.getMessage());
        }
    }
}
