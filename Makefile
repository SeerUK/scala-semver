build: PROJECT_VERSION = $(docker-compose run --rm project_version)
build:
	docker-compose run --rm sbt_build
	tar czvf ./release/seeruk-scala-semver.tar.gz -C ./release ./seeruk
	cp ./release/seeruk-scala-semver.tar.gz ./release/seeruk-scala-semver_${PROJECT_VERSION}.tar.gz

clean:
	rm -rf ./project/target
	rm -rf ./release
	rm -rf ./target

.PHONY: build clean
.SILENT:
