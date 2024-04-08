#!/bin/bash

#!/bin/bash

echo "

MMMMMMMM               MMMMMMMM
M:::::::M             M:::::::M
M::::::::M           M::::::::M
M:::::::::M         M:::::::::M
M::::::::::M       M::::::::::M      eeeeeeeeeeee       mmmmmmm    mmmmmmm      ooooooooooo   rrrrr   rrrrrrrrryyyyyyy           yyyyyyy
M:::::::::::M     M:::::::::::M    ee::::::::::::ee   mm:::::::m  m:::::::mm  oo:::::::::::oo r::::rrr:::::::::ry:::::y         y:::::y
M:::::::M::::M   M::::M:::::::M  e::::::eeeee:::::eem::::::::::mm::::::::::mo:::::::::::::::or:::::::::::::::::ry:::::y       y:::::y
M::::::M M::::M M::::M M::::::Me::::::e     e:::::em::::::::::::::::::::::mo:::::ooooo:::::orr::::::rrrrr::::::ry:::::y     y:::::y
M::::::M  M::::M::::M  M::::::Me:::::::eeeee::::::em:::::mmm::::::mmm:::::mo::::o     o::::o r:::::r     r:::::r y:::::y   y:::::y
M::::::M   M:::::::M   M::::::Me:::::::::::::::::e m::::m   m::::m   m::::mo::::o     o::::o r:::::r     rrrrrrr  y:::::y y:::::y
M::::::M    M:::::M    M::::::Me::::::eeeeeeeeeee  m::::m   m::::m   m::::mo::::o     o::::o r:::::r               y:::::y:::::y
M::::::M     MMMMM     M::::::Me:::::::e           m::::m   m::::m   m::::mo::::o     o::::o r:::::r                y:::::::::y
M::::::M               M::::::Me::::::::e          m::::m   m::::m   m::::mo:::::::::::::::o r:::::r                 y:::::::y
M::::::M               M::::::M e::::::::eeeeeeee  m::::m   m::::m   m::::mo:::::::::::::::o r:::::r                  y:::::y
M::::::M               M::::::M  ee:::::::::::::e  m::::m   m::::m   m::::m oo:::::::::::oo  r:::::r                 y:::::y
MMMMMMMM               MMMMMMMM    eeeeeeeeeeeeee  mmmmmm   mmmmmm   mmmmmm   ooooooooooo    rrrrrrr                y:::::y
                                                                                                                   y:::::y
                                                                                                                  y:::::y
                                                                                                                 y:::::y
                                                                                                                y:::::y
                                                                                                               yyyyyyy                "



# 构建 Spring Boot 项目并生成 JAR 文件
echo "Building Spring Boot project..."
./mvnw clean package -DskipTests

# 检查构建是否成功
if [ $? -ne 0 ]; then
  echo "Failed to build Spring Boot project. Exiting..."
  exit 1
fi

# 启动 Docker Compose
echo "Starting Docker Compose..."
docker-compose up
