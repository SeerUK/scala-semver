build: docker_pull docker_build

clean:
	rm -rf ./project/target
	rm -rf ./release
	rm -rf ./target

docker_build: PROJECT_VERSION = $(shell docker-compose run --rm project_version)
docker_build:
	docker-compose run --rm sbt_build
	tar czvf ./release/seeruk-scala-semver.tar.gz -C ./release ./seeruk
	cp ./release/seeruk-scala-semver.tar.gz ./release/seeruk-scala-semver_${PROJECT_VERSION}.tar.gz

docker_pull:
	docker-compose pull

.PHONY: build clean
.SILENT:
