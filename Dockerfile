FROM maven:3.9.6-eclipse-temurin-21 AS build

# Install dependencies
RUN apt-get update && apt-get install -y \
    wget unzip gnupg curl \
    xvfb libxi6 libgconf-2-4 libnss3 libasound2 libatk1.0-0 \
    libcups2 libgtk-3-0 libxss1 libxshmfence1 fonts-liberation \
    && rm -rf /var/lib/apt/lists/*

# Install Google Chrome
RUN wget -q https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb \
    && apt-get update \
    && apt-get install -y ./google-chrome-stable_current_amd64.deb \
    && rm google-chrome-stable_current_amd64.deb

# Set environment for Chrome + Driver
ENV CHROME_BIN=/usr/bin/google-chrome
ENV CHROMEDRIVER_DIR=/usr/local/bin

WORKDIR /app

COPY . /app

# Run tests
CMD ["mvn", "clean", "test"]
