pipeline {
    agent {
        dockerfile true
    }
    environment {
        DOCKER_BUILDKIT='1'
    }
    stages {
        stage('test') {
            steps {
                sh('''#!/bin/bash -ex
                DOCKER_BUILDKIT=1 docker build --target test \\
                -t ${IMAGE}:test .
                ''')
            }
        }

        stage('app') {
            steps {
                 sh('''#!/bin/bash -ex
                 DOCKER_BUILDKIT=1 docker build --target app \\
                 -t ${IMAGE}:app .
                 ''')
            }
        }
    }
}
