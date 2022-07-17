#!/usr/bin/env groovy
@Library('swg-jenkins-library') _
node{
    properties([
        parameters ([
            choice(name:'TASK', choices: ['','Build','Deploy'].join("\n"), description: 'Task to execute'),
            [$class: 'WHideParameterDefinition', name: 'ENVIRONMENT', defaultValue: '', description: ''],
            [$class: 'WHideParameterDefinition', name: 'RELEASE', defaultValue: '', description: ''],
        ])
    ])

    def repositoryUrl = scm.userRemoteConfigs[0].url

    if(params.TASK == 'Build'){
        summaBuilder(
            repositoryUrl,
            branch: env.BRANCH_NAME,
        );
    }

    if(params.TASK == 'Deploy' || !currentBuild.rawBuild.getCauses()[0].toString().contains('UserIdCause')){
        summaDeployer(
            repositoryUrl,
            branch: env.BRANCH_NAME,
            environment: params.ENVIRONMENT ?: '' ,
            release: params.RELEASE ?: '',
        );
    }
}