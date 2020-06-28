#!/usr/bin/env bash

MODULE=$1

function cleanup() {
    echo "Stopping $MODULE service..."
    kubectl delete all -l=app=$MODULE --now --context=docker-for-desktop
    echo "Stopped"
}
trap cleanup EXIT

kubectl config set-context docker-for-desktop

kubectl delete all -l=app=$MODULE --now --context=docker-for-desktop
kubectl delete configmaps -l=app=$MODULE --now --context=docker-for-desktop
until [[ "$(kubectl get all --context=docker-for-desktop -l=app=$MODULE -o=name)" == "" ]] ; do sleep 1; done

cat configs.yaml | kubectl create --context=docker-for-desktop -f -
cat pod.yaml | kubectl create --context=docker-for-desktop -f -
cat service.yaml | kubectl create --context=docker-for-desktop -f -
until [[ "$(kubectl get pods --context=docker-for-desktop -l=app=$MODULE --field-selector status.phase=Running)" != "" ]] ; do sleep 1; done

kubectl logs $MODULE -f --context=docker-for-desktop
