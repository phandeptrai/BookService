Hướng dẫn chạy nhiều instance của BookServices và đăng ký vào Consul

Mục tiêu
- Cho phép chạy 3 instance của `book-service` và đăng ký cả 3 vào Consul
- Khi một instance chết, Consul sẽ đưa instance đó về trạng thái critical và các client có thể chuyển sang instance khác
- Khi tất cả instance bị tắt (hoặc scale xuống 0) ta có thể khởi tạo lại (bằng Docker Compose restart hoặc Kubernetes)

Tùy chọn trong `ConsulConfig` (biến môi trường):
- CONSUL_HOST (mặc định: localhost)
- CONSUL_PORT (mặc định: 8500)
- SERVICE_NAME (mặc định: book-service)
- SERVICE_ADDRESS (mặc định: auto detect)
- SERVICE_PORT (mặc định: 8080, chỉ dùng khi SIMULATE_INSTANCES=1)
- SIMULATE_INSTANCES (mặc định: 1) — số service entries để đăng ký (dùng cho chạy một process mô phỏng nhiều cổng)
- SIMULATE_BASE_PORT (mặc định: 8080)
- SERVICE_ID — nếu đặt, sử dụng giá trị này làm service id (dành cho 1 instance thực tế)
- SERVICE_INSTANCE_INDEX — nếu đặt, sử dụng để đánh số instance (ví dụ 1..3)
- CONSUL_CHECK_INTERVAL (mặc định: 10s)
- CONSUL_DEREGISTER_AFTER (mặc định: 60s)

Cách chạy 3 container (Docker Compose - mẫu):
- Sử dụng file `docker-compose.yml` kèm theo (ví dụ cân chỉnh IMAGE theo build của bạn).

Kubernetes:
- Khuyến nghị tạo `Deployment` với `replicas: 3` và `liveness/readiness` probes. Consul sẽ thấy 3 endpoints.
- Để Consul tự động đăng ký, dùng sidecar `consul` hoặc `consul-k8s` để quản lý service catalog.

Ghi chú vận hành ngắn
- Consul không "tạo instance mới" khi tất cả các instance chết; Consul chỉ là registry/health-check. Để tự động tạo instance mới bạn cần orchestration (Docker Swarm, Docker Compose restart policies, hoặc Kubernetes Deployment/ReplicaSet).
- Khi instance chết, Consul sẽ mark là critical theo health check và client (hoặc service discovery) sẽ sử dụng các instance healthy còn lại.

Thêm ví dụ `docker-compose.yml` và `k8s-deployment.yaml` trong thư mục project.
