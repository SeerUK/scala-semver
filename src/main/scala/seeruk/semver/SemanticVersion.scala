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

/**
 * Semantic Version
 *
 * An object representation of a successfully parsed semantic version string.
 *
 * @author Elliot Wright <elliot@elliotwright.co>
 */
case class SemanticVersion(
    major: Int,
    minor: Int,
    patch: Int,
    preRelease: Option[List[String]] = None,
    metadata: Option[List[String]] = None) {

  override def toString: String = {
    val preReleaseString = preRelease.map("-" + _.mkString(".")).getOrElse("")
    val metadataString = metadata.map("+" + _.mkString(".")).getOrElse("")

    s"$major.$minor.$patch" + preReleaseString + metadataString
  }
}
