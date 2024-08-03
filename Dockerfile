FROM eclipse-temurin:17-jre
LABEL version="0.1.0" description="ElusiveBot Pattern Match behavior service" maintainer="bryan@degrendel.com"

# Path of the archive without .zip (i.e. app-0.1.0-SNAPSHOT).
ARG base_filename
# Path to directory containing the distribution zip
ARG jar_path=app/build/distributions

RUN apt update && apt install unzip

COPY $jar_path/${base_filename}.zip .
# This is a fustratingly finicky step, mostly because Docker is kinda terrible and also Concourse is kinda terrible.
# So the distributable is in the format of app-VERSION.zip, which contains a single directory called app-VERSION/.
# However, there isn't a great way to insert the version into the Concourse pipeline configuration.  There's no
# scripting in Concourse image build step.  I think it's possible to write it to a YAML file and parse it out using a
# load-var step[1].  Given how painful debugging things is in Concourse, this might take many tries and a lot of pain
# to get working.
#
# So Concourse pipeline renames the zip to app.zip and passes app as base_filename.  However, the zip's folder isn't
# renamed.  mv app-* /app previously worked fine, but doesn't in Vagrant's Docker because ???.  `mv * /app` seems to
# be the magic command that works in Concourse, Vagrant, and standard Debian.  This requires the extracted folder to
# be the only file in a folder, hence creating a temporary hold directory.
#
# (We're in the root directory, so there might be junk in /tmp.  /tmp/hold or whatever would probably be fine)
#
# [1]: https://concourse-ci.org/load-var-step.html
RUN unzip -d hold ${base_filename}.zip && cd hold && ls && mv * /app && cd .. && rmdir hold

CMD [ "/app/bin/app" ]
