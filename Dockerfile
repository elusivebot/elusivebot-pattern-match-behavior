FROM eclipse-temurin:17-jre
LABEL version="0.1.0" description="ElusiveBot Pattern Match behavior service" maintainer="bryan@degrendel.com"

# Path of the archive without .zip (i.e. app-0.1.0-SNAPSHOT).
ARG base_filename
# Path to directory containing the distribution zip
ARG jar_path=app/build/distributions

RUN apt update && apt install unzip

COPY $jar_path/${base_filename}.zip .
RUN unzip ${base_filename}.zip && mv $base_filename /app

CMD [ "./app/bin/app" ]
