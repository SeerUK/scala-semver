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

import scala.language.postfixOps
import scala.util.parsing.combinator.RegexParsers

/**
 * Semantic Versioning Parser, designed to parse Semantic Versioning v2.0.0.
 * See the [[http://semver.org/spec/v2.0.0.html Semantic Versioning website]] for more information.
 *
 * @author Elliot Wright <elliot@elliotwright.co>
 */
class SemanticVersionParser extends RegexParsers {
  /**
   * Parse the given input string, expecting a semantic version string
   *
   * @param input The input to parse
   * @return The parser result
   */
  def parse(input: String): ParseResult[SemanticVersion] = {
    def parser: Parser[SemanticVersion] = versionNumberSection ~ (preReleaseSection ?) ~ (metadataSection ?) ^^ {
      case version ~ preRelease ~ metadata =>
        new SemanticVersion(version.major, version.minor, version.patch, preRelease, metadata)
    }

    parseAll(parser, input)
  }

  private def number: Parser[Int] = """(0|[1-9]\d*)""".r ^^ (_.toInt)
  private def separator: Parser[String] = """\.""".r
  private def preReleaseSeparator: Parser[String] = """-""".r
  private def metadataSeparator: Parser[String] = """\+""".r
  private def identifier: Parser[String] = """([1-9][0-9]*)|(\d*[A-z][A-z0-9]*)|(0)""".r ^^ (_.toString)

  private def versionNumberSection: Parser[VersionSection] = number ~ separator ~ number ~ separator ~ number ^^ {
    case major ~ _ ~ minor ~ _ ~ patch => new VersionSection(major, minor, patch)
  }

  private def identifierSection: Parser[List[String]] = identifier ~ rep(separator ~ identifier) ^^ {
    case start ~ seq => List(start.toString) ++ seq.map(_._2.toString)
  }

  private def metadataSection: Parser[List[String]] = metadataSeparator ~ identifierSection ^^ {
    case _ ~ identifiers => identifiers
  }

  private def preReleaseSection: Parser[List[String]] = preReleaseSeparator ~ identifierSection ^^ {
    case _ ~ identifiers => identifiers
  }

  private case class VersionSection(major: Int, minor: Int, patch: Int)
}
