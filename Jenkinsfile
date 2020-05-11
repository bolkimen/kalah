pipeline {
    agent any
    environment {
        DOCKER_BUILDKIT='1'
    }

    parameters {
        string(
                name: 'RELEASE',
                defaultValue: 'Release version',
                description: 'release version'
        )
    }

    stages {
        stage('test') {
            steps {
                sh('''#!/bin/bash -ex
                DOCKER_BUILDKIT=1 docker build --target test \\
                -t ${env.BUILD_ID}:test .
                ''')
            }
        }

        stage('app') {
            steps {
                 sh('''#!/bin/bash -ex
                 DOCKER_BUILDKIT=1 docker build --target app \\
                 -t ${env.BUILD_ID}:app .
                 ''')
            }
        }
    }
}
