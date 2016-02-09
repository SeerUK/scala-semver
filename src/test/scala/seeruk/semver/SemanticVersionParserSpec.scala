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

import org.scalatest.{FunSpec, BeforeAndAfter}

/**
 * SemanticVersionParser Spec
 *
 * @author Elliot Wright <elliot@elliotwright.co>
 */
class SemanticVersionParserSpec extends FunSpec with BeforeAndAfter {
  val parser = new SemanticVersionParser()

  describe("seeruk.semver.SemanticVersionParser") {
    describe("parse()") {
      it("should return a Success[SemanticVersion] upon successful parsing") {
        val result = parser.parse("1.2.3")

        assert(isSuccessfulParse(result))
      }

      it("should return a Failure upon unsuccessful parsing") {
        val result = parser.parse("1.2")

        assert(isFailedParse(result))
      }

      it("should support parsing major, minor, and patch version strings") {
        val result = parser.parse("1.2.3")

        assert(isSuccessfulParse(result))
      }

      it("should support parsing major versions that are 0") {
        val result = parser.parse("0.1.2")

        assert(isSuccessfulParse(result))
      }

      it("should support parsing minor versions that are 0") {
        val result = parser.parse("1.0.2")

        assert(isSuccessfulParse(result))
      }

      it("should support parsing patch versions that are 0") {
        val result = parser.parse("1.2.0")

        assert(isSuccessfulParse(result))
      }

      it("should support parsing multi-digit major versions") {
        val result = parser.parse("123.4.5")

        assert(isSuccessfulParse(result))
      }

      it("should support parsing multi-digit minor versions") {
        val result = parser.parse("1.234.5")

        assert(isSuccessfulParse(result))
      }

      it("should support parsing multi-digit patch versions") {
        val result = parser.parse("1.2.345")

        assert(isSuccessfulParse(result))
      }

      it("should support parsing combined multi-digit versions") {
        val result = parser.parse("123.456.789")

        assert(isSuccessfulParse(result))
      }

      it("should support parsing a pre-release string") {
        val result = parser.parse("1.2.3-alpha")

        assert(isSuccessfulParse(result))
      }

      it("should support parsing several pre-release strings") {
        val result = parser.parse("1.2.3-alpha.123.foo.bar.baz")

        assert(isSuccessfulParse(result))
      }

      it("should support parsing a metadata string") {
        val result = parser.parse("1.2.3+build")

        assert(isSuccessfulParse(result))
      }

      it("should support parsing several metadata strings") {
        val result = parser.parse("1.2.3+build.123.foo.bar.baz")

        assert(isSuccessfulParse(result))
      }

      it("should support parsing both pre-release and metadata strings") {
        val result1 = parser.parse("1.2.3-alpha+build")
        val result2 = parser.parse("1.2.3-alpha.1+build.123")

        assert(isSuccessfulParse(result1))
        assert(isSuccessfulParse(result2))
      }

      it("should not support parsing major versions that have leading zeros") {
        val result = parser.parse("01.2.3")

        assert(isFailedParse(result))
      }

      it("should not support parsing minor versions that have leading zeros") {
        val result = parser.parse("1.02.3")

        assert(isFailedParse(result))
      }

      it("should not support parsing patch versions that have leading zeros") {
        val result = parser.parse("1.2.03")

        assert(isFailedParse(result))
      }

      it("should not support parsing combined versions that have leading zeros") {
        val result = parser.parse("01.02.03")

        assert(isFailedParse(result))
      }

      it("should not support parsing negative major version numbers") {
        val result = parser.parse("-1.2.3")

        assert(isFailedParse(result))
      }

      it("should not support parsing negative minor version numbers") {
        val result = parser.parse("1.-2.3")

        assert(isFailedParse(result))
      }

      it("should not support parsing negative patch version numbers") {
        val result = parser.parse("1.2.-3")

        assert(isFailedParse(result))
      }

      it("should not support parsing multiple separate pre-release strings") {
        val result = parser.parse("1.2.3-alpha-beta-gamma")

        assert(isFailedParse(result))
      }

      it("should not support parsing multiple separate metadata strings") {
        val result = parser.parse("1.2.3+build+deploy+release")

        assert(isFailedParse(result))
      }

      it("should not support parsing metadata strings before pre-release strings") {
        val result = parser.parse("1.2.3+build-alpha")

        assert(isFailedParse(result))
      }
    }
  }

  private def isSuccessfulParse(result: parser.ParseResult[SemanticVersion]): Boolean = {
    result match {
      case parser.Success(parsed: SemanticVersion, _) => true
      case _ => false
    }
  }

  private def isFailedParse(result: parser.ParseResult[SemanticVersion]): Boolean = {
    result.isInstanceOf[parser.Failure]
  }
}
