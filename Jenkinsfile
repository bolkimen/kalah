pipeline {
    agent any

    parameters {
        string(
                name: 'RELEASE_PREFIX',
                defaultValue: 'rel_',
                description: 'release prefix for container'
        )
    }

    stages {
        stage('test') {
            steps {
                sh('''#!/bin/bash -ex
                DOCKER_BUILDKIT=1 docker build --target test \\
                -t ${RELEASE_PREFIX}_kalah_${BRANCH_NAME}${CHANGE_ID}${BUILD_NUMBER}:test .
                ''')
            }
        }

        stage('app') {
            when {
                 expression {
                     currentBuild.result == null || currentBuild.result == 'SUCCESS'
                   }
                 }
            steps {
                 sh('''#!/bin/bash -ex
                 DOCKER_BUILDKIT=1 docker build --target app \\
                 -t ${RELEASE_PREFIX}_kalah_${BRANCH_NAME}${CHANGE_ID}${BUILD_NUMBER}:app .
                 ''')
            }
        }
    }
}
