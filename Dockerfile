FROM eclipse-temurin:17-jre
LABEL version="0.1.0" description="ElusiveBot Pattern Match behavior service" maintainer="bryan@degrendel.com"

ARG base_filename
ARG jar_path=app/build/distributions

RUN apt update && apt install unzip

# TODO: Update this to an argument, investigate whether there's a good
# way to automagically grab it from gradle.properties and apply to this
# command and the LABEL version.
COPY $jar_path/${base_filename}.zip
RUN unzip ${base_name}.zip

CMD [ "./$base_name/bin/app" ]
