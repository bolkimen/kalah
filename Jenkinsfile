pipeline {
    agent {
        node {
            label "docker-builder"
        }
    }
    stages {
        stage('build') {
            steps {
                sh('''#!/bin/bash -ex
                DOCKER_BUILDKIT=1 docker build --target test --pull \\
                -t ${IMAGE}:swagger .
                ''')
            }
        }
    }
}
