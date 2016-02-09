/**
 * This file is part of the "seeruk/scala-semver" project.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the LICENSE is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package seeruk.semver

import org.scalatest.{BeforeAndAfter, FunSpec}

/**
 * SemanticVersion Spec
 *
 * @author Elliot Wright <elliot@elliotwright.co>
 */
class SemanticVersionSpec extends FunSpec with BeforeAndAfter {
  describe("seeruk.semver.SemanticVersion") {
    describe("toString()") {
      it("should return a string") {
        val semver = new SemanticVersion(1, 2, 3)

        assert(semver.toString.isInstanceOf[String])
      }

      it("should at least return a version string, including a major, minor, and patch number") {
        val semver = new SemanticVersion(1, 2, 3)

        assert(semver.toString == "1.2.3")
      }

      it("should include a pre-release string if one is set") {
        val semver = new SemanticVersion(1, 2, 3, Some(List("alpha")))

        assert(semver.toString == "1.2.3-alpha")
      }

      it("should include several pre-release strings if they are set, separated by a dot") {
        val semver = new SemanticVersion(1, 2, 3, Some(List("alpha", "1")))

        assert(semver.toString == "1.2.3-alpha.1")
      }

      it("should include a metadata string if one is set") {
        val semver = new SemanticVersion(1, 2, 3, None, Some(List("build")))

        assert(semver.toString == "1.2.3+build")
      }

      it("should include several metadata strings if they are set, separated by a dot") {
        val semver = new SemanticVersion(1, 2, 3, None, Some(List("build", "123")))

        assert(semver.toString == "1.2.3+build.123")
      }

      it("should include both a pre-release string, and a metadata string if they are set") {
        val semver1 = new SemanticVersion(1, 2, 3, Some(List("alpha")), Some(List("build")))
        val semver2 = new SemanticVersion(1, 2, 3, Some(List("alpha", "1")), Some(List("build", "123")))

        assert(semver1.toString == "1.2.3-alpha+build")
        assert(semver2.toString == "1.2.3-alpha.1+build.123")
      }
    }
  }
}
